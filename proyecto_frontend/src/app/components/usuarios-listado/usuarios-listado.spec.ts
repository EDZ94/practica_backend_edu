import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuariosListado } from './usuarios-listado';

describe('UsuariosListado', () => {
  let component: UsuariosListado;
  let fixture: ComponentFixture<UsuariosListado>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsuariosListado],
    }).compileComponents();

    fixture = TestBed.createComponent(UsuariosListado);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
