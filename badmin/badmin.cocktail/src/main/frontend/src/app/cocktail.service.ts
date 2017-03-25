import { Injectable } from '@angular/core';
import { Http, Response, Headers} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Cocktail } from './cocktail';

@Injectable()
export class CocktailService{
  private baseUrl: string = 'http://localhost:8080/api';

  constructor(private http : Http){
  }

  getAll(): Observable<Cocktail[]>{
    let cocktails$ = this.http
      .get(`${this.baseUrl}/`, {headers: this.getHeaders()})
      .map(mapCocktails)
      .catch(handleError);
      return cocktails$;
  }

  private getHeaders(){
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    return headers;
  }
}

function mapCocktails(response:Response): Cocktail[]{
   console.log(response);
   return response.json().map(toCocktail)
}

function toCocktail(r:any): Cocktail{
  let cocktail = <Cocktail>({
    id: r.id,
    name: r.name,
    description: r.description,
    recipe: r.recipe
  });
  console.log('Parsed cocktail:', cocktail);
  return cocktail;
}

function handleError (error: any) {
    
  console.error(error);
  let errorMsg = error.message || `Could not retrieve cocktails`
  console.error(errorMsg);

  return Observable.throw(errorMsg);
}
