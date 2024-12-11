import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private client: Client;
  private messageSubject: Subject<string> = new Subject<string>();

  constructor() {
    this.client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      connectHeaders: {},
      debug: (str) => {
        console.log(str);
      },
      reconnectDelay: 1000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      webSocketFactory: () => {
        return new SockJS('http://localhost:8080/ws');
      }
    });

    this.client.onConnect = () => {
      console.log('Connected to WebSocket');
      this.client.subscribe('/topic/messages', (message: Message) => {
        console.log('Received message:', message.body);
        this.messageSubject.next(message.body);
      });
    };

    this.client.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };

    this.client.onWebSocketError = (event) => {
      console.error('WebSocket error: ', event);
    };

    this.client.onWebSocketClose = (event) => {
      console.error('WebSocket closed: ', event);
    };

    this.client.activate();
  }

  sendMessage(message: string) {
    this.client.publish({ destination: '/app/message', body: message });
  }

  getMessages(): Observable<string> {
    return this.messageSubject.asObservable();
  }
}