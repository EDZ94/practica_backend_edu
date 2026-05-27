import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private urlApi = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) { }

  private guardarSesion(usuario: string, contrasena: string): void {
    localStorage.setItem('sesion_nick', usuario);
    localStorage.setItem('sesion_pass', contrasena);
  }

  obtenerUsuarioLogueado(): string {
    return localStorage.getItem('sesion_nick') || '';
  }

  private obtenerClaveLogueada(): string {
    return localStorage.getItem('sesion_pass') || '';
  }

  limpiarSesion(): void {
    localStorage.removeItem('sesion_nick');
    localStorage.removeItem('sesion_pass');
  }

  iniciarSesion(username: string, contrasena: string): Observable<any> {
    const misDatos = { username: username, password: contrasena };
    return this.http.post(`${this.urlApi}/login`, misDatos).pipe(
      tap(() => {
        this.guardarSesion(username, contrasena);
      })
    );
  }

  obtenerUsuarios(): Observable<any[]> {
    const params = new HttpParams()
      .set('nickUsuario', this.obtenerUsuarioLogueado())
      .set('nickContraseña', this.obtenerClaveLogueada());

    return this.http.get<any[]>(this.urlApi, { params });
  }

  crearUsuario(usuario: any): Observable<any> {
    const params = new HttpParams()
      .set('nickUsuario', this.obtenerUsuarioLogueado())
      .set('nickContraseña', this.obtenerClaveLogueada());

    return this.http.post<any>(this.urlApi, usuario, { params });
  }

  actualizarUsuario(id: number, usuario: any): Observable<any> {
    const params = new HttpParams()
      .set('nickUsuario', this.obtenerUsuarioLogueado())
      .set('nickContraseña', this.obtenerClaveLogueada());

    return this.http.put<any>(`${this.urlApi}/${id}`, usuario, { params });
  }

  eliminarUsuario(id: number): Observable<any> {
    const params = new HttpParams()
      .set('nickUsuario', this.obtenerUsuarioLogueado())
      .set('nickContraseña', this.obtenerClaveLogueada());

    return this.http.delete<any>(`${this.urlApi}/${id}`, { params });
  }
}