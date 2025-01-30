import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Page} from "../../model/page";
import {Product} from "../../model/product";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {

  nameFilter: string = '';

  @Output() filterApplied = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  applyFilter() {
    this.filterApplied.emit(this.nameFilter);
  }

}
