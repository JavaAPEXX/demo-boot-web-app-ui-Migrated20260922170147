UNCONFIRMED Backend Contract
UNCONFIRMED Session/model value resolution
UNCONFIRMED Request-security delivery
UNCONFIRMED Password submission
Duplicate endpoint `/registration` noted
// pages/migrated-repo/Login.tsx
import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { login } from '../services/authService';

const Login = () => {
  const history = useHistory();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await login({ username, password, passwordConfirm });
      history.push('/welcome');
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>Username:</label>
      <input type="text" value={username} onChange={(event) => setUsername(event.target.value)} />
      <br />
      <label>Password:</label>
      <input type="password" value={password} onChange={(event) => setPassword(event.target.value)} />
      <br />
      <label>Confirm Password:</label>
      <input type="password" value={passwordConfirm} onChange={(event) => setPasswordConfirm(event.target.value)} />
      <br />
      <button type="submit">Login</button>
    </form>
  );
};

export default Login;
// pages/migrated-repo/Registration.tsx
import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { registration } from '../services/authService';

const Registration = () => {
  const history = useHistory();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await registration({ username, password, passwordConfirm });
      history.push('/welcome');
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>Username:</label>
      <input type="text" value={username} onChange={(event) => setUsername(event.target.value)} />
      <br />
      <label>Password:</label>
      <input type="password" value={password} onChange={(event) => setPassword(event.target.value)} />
      <br />
      <label>Confirm Password:</label>
      <input type="password" value={passwordConfirm} onChange={(event) => setPasswordConfirm(event.target.value)} />
      <br />
      <button type="submit">Register</button>
    </form>
  );
};

export default Registration;
// pages/migrated-repo/Welcome.tsx
import React from 'react';

const Welcome = () => {
  return <div>Welcome!</div>;
};

export default Welcome;
// services/authService.ts
import axios from 'axios';

const login = async (credentials) => {
  try {
    const response = await axios.post('/login', credentials);
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

const registration = async (credentials) => {
  try {
    const response = await axios.post('/registration', credentials);
    return response.data;
  } catch (error) {
    console.error(error);
  }
};

export { login, registration };
// services/apiClient.ts
import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'https://example.com/api',
});

export default apiClient;
// types/document.ts
export interface Document {
  id: number;
  title: string;
  link: string;
  description: string;
}

export default Document;
// types/role.ts
export interface Role {
  id: number;
  name: string;
}

export default Role;
// types/user.ts
export interface User {
  id: number;
  username: string;
  password: string;
  passwordConfirm: string;
  roles: Role[];
}

export default User;