export class AddLineItemDto {

  constructor(public productId: bigint,
              public qty: number,
              public color: string,
              public material: string) {
  }
}
