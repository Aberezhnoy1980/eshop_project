export class Order {

  constructor(public id: bigint,
              public price: number,
              public orderDate: string,
              public status: string) {
  }
}
