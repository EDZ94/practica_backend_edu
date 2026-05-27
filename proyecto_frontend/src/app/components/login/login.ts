import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  usuarioInput: string = '';
  contrasenaInput: string = '';
  errorMensaje: string = '';

  constructor(
    private usuarioService: UsuarioService, 
    private router: Router,
    private cdr: ChangeDetectorRef // inycecto el ChangeDetectorRef para controlar manualmente la detección de cambios
  ) {}

  alEnviarFormulario(): void {
    this.errorMensaje = '';
    this.cdr.detectChanges(); // Fuerzzo a Angular a actualizar la vista antes de iniciar el proceso de login

    this.usuarioService.iniciarSesion(this.usuarioInput, this.contrasenaInput).subscribe({
      next: (respuesta) => {
        console.log('¡Login correcto!', respuesta);
        this.router.navigate(['/usuarios']).then(() => {
          this.cdr.detectChanges(); // Forzamos el salto de pantalla inmediato
        });
      },
      error: (err) => {
        console.error('Error capturado en login:', err);
        this.errorMensaje = 'El usuario o la contraseña no son válidos.';
        this.cdr.detectChanges(); // Fuerzo a Angular a pintar el error en el acto
      }
    });
  }
}