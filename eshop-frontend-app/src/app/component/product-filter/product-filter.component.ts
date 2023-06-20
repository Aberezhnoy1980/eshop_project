import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProductFilters} from "../../model/ProductFilters";
import {Category} from "../../model/category";
import {Brand} from "../../model/brand";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {

  @Output() filterApplied = new EventEmitter<ProductFilters>();

  @Input() categories: Category [] = [];

  @Input() brands: Brand [] = [];

  productFilters = new ProductFilters('', '', '', -1, -1, 0, 10000000);

  constructor() {
  }

  ngOnInit(): void {

  }

  applyFilter() {
    this.filterApplied.emit(this.productFilters);
  }

  reset() {
    this.filterApplied.emit(new ProductFilters('', '', '', -1, -1, 0, 10000000));
    }
}
