import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductGalleryPageComponent } from './page/product-gallery-page/product-gallery-page.component';
import { ProductInfoPageComponent } from './page/product-info-page/product-info-page.component';
import { NavBarComponent } from './component/nav-bar/nav-bar.component';
import { FooterComponent } from './component/footer/footer.component';
import { ProductFilterComponent } from './component/product-filter/product-filter.component';
import { GalleryComponent } from './component/gallery/gallery.component';
import { PaginationComponent } from './component/pagination/pagination.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { CartItemComponent } from './component/cart-item/cart-item.component';
import { CartPageComponent } from './page/cart-page/cart-page.component';
import {LoginPageComponent} from "./page/login-page/login-page.component";
import {OrderPageComponent} from "./page/order-page/order-page.component";
import {OrderItemComponent} from "./component/order-item/order-item.component";
import {OrderEditPageComponent} from "./page/order-edit-page/order-edit-page.component";
import {UnauthorizedInterceptor} from "./helper/unauthorized-interceptor";


@NgModule({
  declarations: [
    AppComponent,
    ProductGalleryPageComponent,
    ProductInfoPageComponent,
    NavBarComponent,
    FooterComponent,
    ProductFilterComponent,
    GalleryComponent,
    PaginationComponent,
    CartItemComponent,
    OrderItemComponent,
    OrderEditPageComponent,
    CartPageComponent,
    LoginPageComponent,
    OrderPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: UnauthorizedInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
