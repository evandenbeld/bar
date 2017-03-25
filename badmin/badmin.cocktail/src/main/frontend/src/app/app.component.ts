import { Component } from '@angular/core';
import { CocktailService } from './cocktail.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [CocktailService]
})
export class AppComponent {
  title = 'Its reloaded to';
  templateUrl: './captions.html';
}
