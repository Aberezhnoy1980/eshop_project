import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {Product} from "../../model/product";
import {Page} from "../../model/page";

@Component({
  selector: 'app-product-gallery-page',
  templateUrl: './product-gallery-page.component.html',
  styleUrls: ['./product-gallery-page.component.scss']
})
export class ProductGalleryPageComponent implements OnInit {

  products: Product[] = [];

  page?: Page;

  nameFilter?: string;

  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.productService.findAll().subscribe(res => {
      console.log("Loading products");
      this.page = res;
      this.products = res.content;
    }, err => {
      console.log(`Error loading products ${err}`);
    });
  }

  goToPage(page: number) {
    this.productService.findAll(page, this.nameFilter).subscribe(res => {
      console.log("loading products");
      this.page = res;
      this.products = res.content;
    }, err => {
      console.log(`Error loading products ${err}`);
    });
  }

  filterApplied($event: string) {
    this.productService.findAll(1, $event).subscribe(res => {
      this.nameFilter = $event;
      this.page = res;
      this.products = res.content;
    }, err => {
      console.log(`Error loading item ${err}`);
    });
  }
}
