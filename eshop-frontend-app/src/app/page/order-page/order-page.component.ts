import {Component, OnInit} from '@angular/core';
import {Order} from "../../model/order";
import {OrderService} from "../../service/order.service";
import {OrderStatusService} from "../../service/order-status.service";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.scss']
})
export class OrderPageComponent implements OnInit {

  orders: Order[] = [];

  constructor(public orderService: OrderService,
              private authService: AuthService,
              private router: Router,
              private orderStatusService: OrderStatusService) {
  }

  ngOnInit(): void {
    this.orderService.findOrdersByCurrentUser()
      .subscribe(orders => {
        this.orders = orders;
      }, err => {
        console.log(`Error retrieving orders ${err}`);
      });
    this.orderStatusService.onMessage('/order_out/order')
      .subscribe(msg => {
        let updated = this.orders.find(order => order.id === msg.orderId);
        if (updated) {
          updated.status = msg.status;
        }
      });
  }

  private _createOrderCallback() {
    this.orderService.createOrder()
      .subscribe();
  }

  updateOrder(id: bigint) {
    console.log(`invoke method updateOrder from order page with order id ${id}`)
    if (!this.authService.isAuthenticated()) {
      this.authService.redirectUrl = '/order';
      this.authService.callbackAfterSuccess = this._createOrderCallback.bind(this);
      this.router.navigateByUrl('/login');
      return;
    }
    this.orderService.findItemsByCurrentOrder(id)
      .subscribe(() => this.router.navigateByUrl('/order/' + id));
  }

  clear() {
    return this.orderService.clear().subscribe(
      res => {
        this.orders = res;
      });
  }

  deleteOrder(order: Order) {
    console.log("invoke order remove")
    this.orderService.removeOrder(order)
      .subscribe(
        () => {
          this.ngOnInit();
        });
  }
}
