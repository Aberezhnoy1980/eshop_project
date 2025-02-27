import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {AddLineItemDto} from "../../model/add-line-item-dto";
import {CartService} from "../../service/cart.service";

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {

  @Input() products: Product[] = [];

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
  }

  addToCart(id: number) {
    this.cartService.addToCart(new AddLineItemDto(id, 1, "", ""))
      .subscribe();
  }

}
