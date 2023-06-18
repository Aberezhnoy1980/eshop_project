import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {Product} from "../../model/product";
import {Page} from "../../model/page";
import {OrderStatusService} from "../../service/order-status.service";
import {ProductFilters} from "../../model/ProductFilters";
import {Category} from "../../model/category";
import {CategoryService} from "../../service/category.service";
import {Brand} from "../../model/brand";
import {BrandService} from "../../service/brand.service";

@Component({
  selector: 'app-product-gallery-page',
  templateUrl: './product-gallery-page.component.html',
  styleUrls: ['./product-gallery-page.component.scss']
})
export class ProductGalleryPageComponent implements OnInit {

  products: Product [] = []; // pass to gallery component

  categories: Category [] = [];

  brands: Brand [] = [];

  page?: Page;

  productFilters?: ProductFilters;

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private brandService: BrandService) {
  }

  ngOnInit(): void {
    this.productService.findAll().subscribe(res => {
      console.log("Loading products");
      this.page = res;
      this.products = res.content;
    }, err => {
      console.log(`Error loading products ${err}`);
    });
    this.categoryService.findALl().subscribe(res => this.categories = res);
    this.brandService.findALl().subscribe(res => this.brands = res);
  }

  goToPage(page: number) {
    this.productService.findAll(this.productFilters, page).subscribe(res => {
      console.log("loading products");
      this.page = res;
      this.products = res.content;
    }, err => {
      console.log(`Error loading products ${err}`);
    });
  }

  filterApplied($event: ProductFilters) {
    this.productService.findAll($event, 1).subscribe(res => {
      this.productFilters = $event;
      this.page = res;
      this.products = res.content;
    }, err => {
      console.log(`Error loading products ${err}`);
    });
  }
}
