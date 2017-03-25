import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { CarouselModule } from 'ng2-bootstrap/carousel';

import { AppComponent } from './app.component';
import { CocktailsComponent } from './cocktail.component';

@NgModule({
  declarations: [
    AppComponent, CocktailsComponent
  ],
  imports: [
    CarouselModule.forRoot(),
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
