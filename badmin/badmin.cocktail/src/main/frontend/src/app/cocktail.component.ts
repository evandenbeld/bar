import { Component, OnInit } from '@angular/core';
import { Cocktail } from './cocktail';
import { CocktailService } from './cocktail.service';

@Component({
  selector: 'cocktails',
  templateUrl: './cocktail.component.html'
})
export class CocktailsComponent implements OnInit{
  cocktails: Cocktail[] = [];
  errorMessage: string = '';
  isLoading: boolean = true;

  constructor(private cocktailService : CocktailService){ }

  ngOnInit(){
    this.cocktailService
      .getAll()
      .subscribe(
         /* happy path */ p => this.cocktails = p,
         /* error path */ e => this.errorMessage = e,
         /* onComplete */ () => this.isLoading = false);
  }
}
