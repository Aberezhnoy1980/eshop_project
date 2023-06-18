import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";
import {Observable} from "rxjs";
import {OrderLineItem} from "../model/order-line-item";
import {AllOrderItemsDto} from "../model/all-order-items-dto";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  // Orders

  public findOrdersByCurrentUser(){
    return this.http.get<Order[]>('/api/v1/order/all');
  }

  public createOrder() {
    return this.http.post<any>('/api/v1/order', {});
  }

  public updateOrder(order: Order) {
    return this.http.put("api/v1/order", ({
      body: order
    }));
  }

  removeOrder(order: Order) {
    return this.http.delete('api/v1/order', ({
      body: order
    }));
  }

  public clear() {
    return this.http.get<Order[]>('api/v1/order/clear');
  }

  // OrderItems

  findItemsByCurrentOrder(id: bigint) : Observable<AllOrderItemsDto> {
    console.log(`invoke method findItemsByCurrentOrder from order service with order id ${id}`);
    return this.http.get<AllOrderItemsDto>('/api/v1/order/item/' + id);
  }

  updateOrderLineItem(orderLineItem: OrderLineItem) : Observable<OrderLineItem> {
    console.log("invoke updateOrderLineItem method from order service")
    return this.http.put<OrderLineItem>('api/v1/order/item', ({
      body: orderLineItem
    }));
  }

  removeOrderLineItem(orderLineItem: OrderLineItem) {
    return this.http.delete('api/v1/order/item', ({
      body: orderLineItem
    }));
  }
}
