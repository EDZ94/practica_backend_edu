import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario';

@Component({
  selector: 'app-usuarios-listado',
  standalone: true,
  imports: [CommonModule, FormsModule], // Uso el FormsModule para que funcionen los enlaces en mis formularios
  templateUrl: './usuarios-listado.html',
  styleUrl: './usuarios-listado.css'
})
export class UsuariosListado implements OnInit {
  
  listaUsuarios: any[] = [];
  usuarioSeleccionadoId: number | null = null;

  // Modales flotantes de la pantalla
  mostrarModal: boolean = false;
  mostrarModalBorrar: boolean = false; 
  
  esEdicion: boolean = false;
  modalTitulo: string = 'Create User';

  // Combos obligatorios 
  listaGeneros: any[] = [
    { id: 1, nombre: 'Hombre' },
    { id: 2, nombre: 'Mujer' }
  ];
  listaPuestos: any[] = [
    { id: 1, nombre: 'Senior Project Manager' },
    { id: 2, nombre: 'Senior Architect' },
    { id: 3, nombre: 'Head Of Operations' },
    { id: 4, nombre: 'Automation Tester' }
  ];

  usuarioForm: any = {
    id: null,
    nickUsuario: '',
    contrasena: '',
    nombre: '',
    primerApellido: '',
    segundoApellido: '',
    fechaNacimiento: '',
    horaDesayuno: '',
    idGenero: 1,
    idPuesto: null,
    esAdmin: false
  };

  listaDireccionesModal: any[] = [];
  direccionSeleccionadaIndex: number | null = null;
  
  direccionInputCalle: string = '';
  direccionInputNumero: number | null = null;

  constructor(
    private usuarioService: UsuarioService, 
    private router: Router,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit(): void {
    this.cargarUsuariosDesdeFondo();
  }

  cargarUsuariosDesdeFondo(): void {
    this.usuarioService.obtenerUsuarios().subscribe({
      next: (datos) => {
        this.listaUsuarios = datos;
        if (this.listaUsuarios.length > 0 && !this.usuarioSeleccionadoId) {
          this.usuarioSeleccionadoId = this.listaUsuarios[0].id;
        }
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al traer usuarios:', err)
    });
  }


  obtenerEdad(fechaNacimientoString: string): number {
    if (!fechaNacimientoString) return 0;
    
    // Convierto el texto que me llega de la fecha de nacimiento en un objeto de tipo fecha
    const cumple = new Date(fechaNacimientoString);
    const hoy = new Date();
    
    // Resto los años directamente para tener una aproximación rápida
    let edadAsignada = hoy.getFullYear() - cumple.getFullYear();
    const diferenciaMeses = hoy.getMonth() - cumple.getMonth();
    
    // Ajusto si este año todavía no ha sido su cumpleaños
    if (diferenciaMeses < 0 || (diferenciaMeses === 0 && hoy.getDate() < cumple.getDate())) {
      edadAsignada--;
    }
    return edadAsignada;
  }

  cambiarSeleccion(id: number): void {
    this.usuarioSeleccionadoId = id;
    this.cdr.detectChanges();
  }

  abrirModalCrear(): void {
    this.esEdicion = false;
    this.modalTitulo = 'Create User';
    this.listaDireccionesModal = [];
    this.limpiarTodoElFormulario();
    this.mostrarModal = true;
    this.cdr.detectChanges();
  }

  abrirModalEditar(): void {
    if (!this.usuarioSeleccionadoId) return;
    this.esEdicion = true;
    this.modalTitulo = 'Update User';
    this.limpiarCamposDireccion();

    const seleccionado = this.listaUsuarios.find(u => u.id === this.usuarioSeleccionadoId);
    if (seleccionado) {
      this.usuarioForm = { ...seleccionado };
      this.listaDireccionesModal = seleccionado.direcciones || [];
    }
    this.mostrarModal = true;
    this.cdr.detectChanges();
  }

  ejecutarGuardar(): void {
    this.mostrarModal = false;
    this.cargarUsuariosDesdeFondo();
  }

  ejecutarCancelar(): void {
    this.mostrarModal = false;
    this.cdr.detectChanges();
  }

  agregarDireccionLinea(): void {
    if (!this.direccionInputCalle.trim()) return;

    const nueva = {
      nombreCalle: this.direccionInputCalle,
      numeroCalle: this.direccionInputNumero,
      esPrincipal: this.listaDireccionesModal.length === 0
    };

    this.listaDireccionesModal.push(nueva);
    this.limpiarCamposDireccion();
    this.cdr.detectChanges();
  }

  modificarDireccionLinea(): void {
    if (this.direccionSeleccionadaIndex === null || !this.direccionInputCalle.trim()) return;

    this.listaDireccionesModal[this.direccionSeleccionadaIndex].nombreCalle = this.direccionInputCalle;
    this.listaDireccionesModal[this.direccionSeleccionadaIndex].numeroCalle = this.direccionInputNumero;
    
    this.limpiarCamposDireccion();
    this.cdr.detectChanges();
  }

  eliminarDireccionLinea(): void {
    if (this.direccionSeleccionadaIndex === null) return;
    
    const eraPrincipal = this.listaDireccionesModal[this.direccionSeleccionadaIndex].esPrincipal;
    this.listaDireccionesModal.splice(this.direccionSeleccionadaIndex, 1);
    
    if (eraPrincipal && this.listaDireccionesModal.length > 0) {
      this.listaDireccionesModal[0].esPrincipal = true;
    }

    this.limpiarCamposDireccion();
    this.cdr.detectChanges();
  }

  marcarDireccionPrincipal(indexMarcado: number): void {
    this.listaDireccionesModal.forEach((dir, idx) => {
      dir.esPrincipal = (idx === indexMarcado);
    });
    this.cdr.detectChanges();
  }

  seleccionarDireccionFila(index: number): void {
    this.direccionSeleccionadaIndex = index;
    const dir = this.listaDireccionesModal[index];
    this.direccionInputCalle = dir.nombreCalle;
    this.direccionInputNumero = dir.numeroCalle;
    this.cdr.detectChanges();
  }

  limpiarTodoElFormulario(): void {
    this.usuarioForm = { id: null, nickUsuario: '', contrasena: '', nombre: '', primerApellido: '', segundoApellido: '', fechaNacimiento: '', horaDesayuno: '', idGenero: 1, idPuesto: null, esAdmin: false };
    this.limpiarCamposDireccion();
  }

  limpiarCamposDireccion(): void {
    this.direccionSeleccionadaIndex = null;
    this.direccionInputCalle = '';
    this.direccionInputNumero = null;
  }

  ejecutarBorrado(): void {
    if (!this.usuarioSeleccionadoId) return;
    this.mostrarModalBorrar = true;
    this.cdr.detectChanges();
  }

  confirmarBorrar(): void {
    this.mostrarModalBorrar = false;
    this.usuarioSeleccionadoId = null; 
    this.cargarUsuariosDesdeFondo();
  }

  cancelarBorrar(): void {
    this.mostrarModalBorrar = false;
    this.cdr.detectChanges();
  }

  ejecutarLogout(): void {
    this.usuarioService.limpiarSesion();
    this.router.navigate(['/login']);
  }
}