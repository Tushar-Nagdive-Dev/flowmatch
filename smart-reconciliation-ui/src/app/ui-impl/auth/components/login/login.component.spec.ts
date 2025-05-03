import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: jasmine.SpyObj<AuthService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('AuthService', ['login']);

    await TestBed.configureTestingModule({
      imports: [LoginComponent, ReactiveFormsModule],
      declarations: [LoginComponent],
      providers: [{ provide: AuthService, useValue: spy }]
    }).compileComponents();
    
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should login successfully', () => {
    authService.login.and.returnValue(of({ token: 'mock-jwt' }));
    component.loginForm.setValue({ username: 'user_match', password: 'user_match@123' });

    component.onSubmit();

    expect(authService.login).toHaveBeenCalledWith({username: 'user_match', password: 'user_match@123'});
  });
  
  it('should handle login error', () => {
    authService.login.and.returnValue(throwError(() => ({ status: 401 })));
    component.loginForm.setValue({ username: 'user', password: 'wrongpass' });

    component.onSubmit();

    expect(component.errorMessage).toEqual('Login failed. Please try again.');
  });
});
