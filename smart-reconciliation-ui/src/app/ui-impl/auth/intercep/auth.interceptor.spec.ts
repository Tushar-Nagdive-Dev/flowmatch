import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth.interceptor';
import { AuthService } from '../services/auth.service';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

class MockAuthService {
  private token: string | null = null;

  setToken(token: string | null) {
    this.token = token;
  }

  getToken() {
    return this.token;
  }
}

describe('AuthInterceptor (Functional HttpInterceptorFn)', () => {
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  let mockAuthService: MockAuthService;

  beforeEach(() => {
    mockAuthService = new MockAuthService();

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        {
          provide: HTTP_INTERCEPTORS,
          useValue: AuthInterceptor,
          multi: true
        },
        provideHttpClient(withInterceptors([AuthInterceptor]))
      ]
    });

    httpMock = TestBed.inject(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should add Authorization header when token exists', () => {
    mockAuthService.setToken('mock-jwt-token');

    httpClient.get('/test-api').subscribe();

    const req = httpMock.expectOne('/test-api');
    expect(req.request.headers.has('Authorization')).toBeTrue();
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-jwt-token');

    req.flush({});
  });

  it('should NOT add Authorization header when token does NOT exist', () => {
    mockAuthService.setToken(null); // No token

    httpClient.get('/test-api').subscribe();

    const req = httpMock.expectOne('/test-api');
    expect(req.request.headers.has('Authorization')).toBeFalse();

    req.flush({});
  });
});
