import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {OrderLineItem} from "../../model/order-line-item";
import {OrderService} from "../../service/order.service";
import {LineItem} from "../../model/line-item";

@Component({
  selector: '[app-order-item]',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.scss']
})
export class OrderItemComponent implements OnInit {

  @Output() updated = new EventEmitter();

  private _orderLineItem?: OrderLineItem;

  qty: number = 0;

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {
  }

  @Input()
  set orderLineItem(value: OrderLineItem | undefined) {
    this._orderLineItem = value;
    this.qty = value ? value.qty : 0;
  }

  get orderLineItem(): OrderLineItem | undefined {
    return this._orderLineItem;
  }

  updateOrderLineItem() {
    if (this._orderLineItem) {
      this.orderService.updateOrderLineItem(this._orderLineItem)
        .subscribe(
          res => {
            this.updated.emit();
          },
          error => {
            console.log(error)
          }
        )
    }
  }

  deleteOrderLineItem() {
    if (this._orderLineItem) {
      this.orderService.removeOrderLineItem(this._orderLineItem)
        .subscribe(
          res => {
            this.updated.emit();
          },
          error => {
            console.log(error)
          }
        )
    }
  }
}
