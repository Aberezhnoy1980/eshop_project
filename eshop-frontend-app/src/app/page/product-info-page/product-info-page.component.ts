import {Component, Input, OnInit} from '@angular/core';
import {AddLineItemDto} from "../../model/add-line-item-dto";
import {CartService} from "../../service/cart.service";
import {ProductService} from "../../service/product.service";
import {Product} from "../../model/product";
import {Brand} from "../../model/brand";
import {Category} from "../../model/category";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-product-info-page',
  templateUrl: './product-info-page.component.html',
  styleUrls: ['./product-info-page.component.scss']
})
export class ProductInfoPageComponent implements OnInit {

  // We need product or id, so, input decorator
  @Input() product: Product = new Product(BigInt(5), '', new Brand(0, ''), '', 0, new Category(BigInt(0), ''), []);
  // product?: Product;

  // id: bigint = BigInt(5);

  id: bigint = (<any>this.activatedRoute.snapshot.params).id;


  constructor(private cartService: CartService,
              private productService: ProductService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    console.log(`invoke method ngInit from product-info: find product with id ${this.id}`)
    this.productService.findProductById(this.id)
      .subscribe(res => this.product = res);
  }

  addToCart(id: bigint) {
    this.cartService.addToCart(new AddLineItemDto(id, 1, "", ""))
      .subscribe();
  }
}
