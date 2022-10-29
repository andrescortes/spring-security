export interface JwtDtoInterface {
  token: string;
  type: string;
  username: string;
  authorities: string[];
}
