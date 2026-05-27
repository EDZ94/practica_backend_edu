import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { UsuariosListado } from './components/usuarios-listado/usuarios-listado';

export const routes: Routes = [
  
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  
  // Ruta para la pantalla de Login
  { path: 'login', component: Login },
  
  // Ruta para la pantalla donde irá la tabla de usuarios
  { path: 'usuarios', component: UsuariosListado }
];