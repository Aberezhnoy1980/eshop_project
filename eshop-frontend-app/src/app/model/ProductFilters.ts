export class ProductFilters {

  constructor(public name: string,
              public categoryName: string,
              public brandName: string,
              public categoryId: number,
              public brandId: number,
              public minPrice: number,
              public maxPrice: number
  ) {
  }
}
