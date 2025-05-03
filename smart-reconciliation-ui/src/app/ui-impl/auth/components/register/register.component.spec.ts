import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: jasmine.SpyObj<AuthService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('AuthService', ['register']);

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, RegisterComponent, ReactiveFormsModule],
      declarations: [RegisterComponent],
      providers: [{ provide: AuthService, useValue: spy }]
    }).compileComponents();
    
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should register successfully', () => {
    authService.register.and.returnValue(of('User registered successfully'));
    component.registerForm.setValue({ username: 'user_match_test', email: 'userMatchTest@mail.com', password: 'usettest@123' });

    component.onSubmit();

    expect(authService.register).toHaveBeenCalledWith({
      username: 'user_match_test',
      email: 'userMatchTest@mail.com',
      password: 'usettest@123'
    });
  });

  it('should handle register error', () => {
    authService.register.and.returnValue(throwError(() => ({ status: 400 })));
    component.registerForm.setValue({ username: 'user_match_test', email: 'userMatchTest@mail.com', password: 'usettest@123' });

    component.onSubmit();

    expect(component.errorMessage).toEqual('Registration failed. Please try again.');
  });
});
