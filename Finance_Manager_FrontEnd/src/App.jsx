import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import Home from "./pages/Home.jsx";
import Income from "./pages/Income.jsx";
import Expense from "./pages/Expense.jsx";
import Category from "./pages/Category.jsx";
import Filter from "./pages/Filter.jsx";
import Login from "./pages/Login.jsx";
import Signup from "./pages/Signup.jsx";
import {Toaster} from "react-hot-toast";
import LandingPage from "./pages/LandingPage.jsx";
import ResetPassword from "./pages/ResetPassword.jsx";
import ForgotPassword from "./pages/ForgotPassword.jsx";


const App = () => {

  return (
      <>
        <Toaster
          toastOptions={{
            style: {
              border: "2px solid #bbf7d0",
              background: "#f0fdf4",
              color: "#14532d",
            },
            success: {
              style: {
                borderColor: "#86efac",
                background: "#f0fdf4",
                color: "#166534",
              },
            },
            error: {
              style: {
                borderColor: "#fda4af",
                background: "#fff1f2",
                color: "#9f1239",
              },
            },
          }}
        />
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Root />} />
            <Route path="/home" element={<LandingPage />} />
            <Route path="/dashboard" element={<Home />} />
            <Route path="/income" element={<Income />} />
            <Route path="/expense" element={<Expense />} />
            <Route path="/category" element={<Category />} />
            <Route path="/filter" element={<Filter />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/reset-password" element={<ResetPassword />} />
            <Route path="/forgot-password" element={<ForgotPassword />} />
          </Routes>
        </BrowserRouter>
      </>
  )
}

const Root = () => {
    const isAuthenticated = !!localStorage.getItem("token");
    return isAuthenticated ? (
        <Navigate to="/dashboard" />
    ) : (
        <Navigate to="/home" />
    );
}

export default App;
