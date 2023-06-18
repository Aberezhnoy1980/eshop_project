import {Order} from "./order";

export class AllOrderItemsDto {

  constructor(public orders: Order[],
              public subtotal: number) {
  }
}
