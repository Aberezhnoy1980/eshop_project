import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CartService} from "../../service/cart.service";
import {OrderService} from "../../service/order.service";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AllOrderItemsDto} from "../../model/all-order-items-dto";

@Component({
  selector: 'app-order-edit-page',
  templateUrl: './order-edit-page.component.html',
  styleUrls: ['./order-edit-page.component.scss']
})
export class OrderEditPageComponent implements OnInit {

  @Output() updated = new EventEmitter();

  content?: AllOrderItemsDto;

  @Input() id: bigint = (<any>this.activatedRoute.snapshot.params).id;

  constructor(private cartService: CartService,
              private orderService: OrderService,
              private authService: AuthService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.orderUpdated();
  }

  orderUpdated() {
    console.log(`invoke method orderUpdate from order edit page with order id ${(this.id)}`)
    this.orderService.findItemsByCurrentOrder(this.id)
      .subscribe(
        res => {
          this.content = res;
          this.updated.emit();
        }
      );
  }
}
