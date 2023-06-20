export class OrderLineItem {

  constructor(public id: bigint,
              public orderId: bigint,
              public productId: bigint,
              public productName: string,
              public price: number,
              public qty: number,
              public color: string,
              public material: string,
              public orderItemTotal: number) {
  }
}
