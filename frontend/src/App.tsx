import { useEffect, useState } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';

import Loader from './common/Loader';
import PageTitle from './components/PageTitle';
import SignIn from './pages/Authentication/SignIn';
import ECommerce from './pages/Dashboard/ECommerce';
import Settings from './pages/Settings';
import DefaultLayout from './layout/DefaultLayout';
import ProtectedRoutes from './utils/ProtectedRoutes';
import Clients from './pages/Clients'
import Suppliers from './pages/Suppliers.tsx';
import CheckDefaultRoute from './utils/CheckDefaultRoute.tsx';

function App() {
    const [loading, setLoading] = useState<boolean>(true);
    const { pathname } = useLocation();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [pathname]);

    useEffect(() => {
        setTimeout(() => setLoading(false), 1000);
    }, []);

    return loading ? (
        <Loader />
    ) : (
        <Routes>
          <Route path="/*" element={<CheckDefaultRoute />} />          <Route
                path="/auth/login"
                element={
                    <>
                        <SignIn />
                    </>
                }
            />
            <Route element={<DefaultLayout><ProtectedRoutes /></DefaultLayout>}>
              <Route
                    index
                    path="/dashboard"
                    element={
                        <>
                            <PageTitle title="POW Store" />
                            <ECommerce />
                        </>
                    }
                />
                <Route
                    path="/clients"
                    element={
                        <>
                            <PageTitle title="Clients" />
                            <Clients />
                        </>
                    }
                />
              <Route
                path="/suppliers"
                element={
                  <>
                    <PageTitle title="Suppliers" />
                    <Suppliers />
                  </>
                }
              />
                <Route
                    path="/settings"
                    element={
                        <>
                            <PageTitle title="Settings" />
                            <Settings />
                        </>
                    }
                />
            </Route>
        </Routes>
    );
}

export default App;
