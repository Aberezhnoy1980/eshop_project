import {OrderLineItem} from "./order-line-item";

export class AllOrderItemsDto {

  constructor(public orderLineItems: OrderLineItem[],
              public subtotal: number) {
  }
}
