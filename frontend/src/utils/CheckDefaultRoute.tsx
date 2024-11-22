import { Navigate } from 'react-router-dom';

const CheckDefaultRoute = () => {
  const token = localStorage.getItem('token');
  return token ? <Navigate to="/dashboard" /> : <Navigate to="/auth/login" />;
};

export default CheckDefaultRoute;
