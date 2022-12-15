import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import swal from "sweetalert";
import "../../styles/Login.css";
import { API } from "../Config/ApiUrl";
const URL = API("login");

const Login = () => {
  const [user, setUser] = useState("");
  const [pwd, setPwd] = useState("");
  const navigate = useNavigate();

  const ingresar = async (e) => {
    e.preventDefault();
    try {
      const login = await axios({
        method: "POST",
        url: URL,
        headers: {
          user: user,
          pwd: pwd,
        },
      });
      swal(
        "Acceso Autorizado",
        "Bievenid@ " + login.data.userName,
        "success"
      ).then((value) => {
        sessionStorage.setItem("id", login.data.idUsuario);
        sessionStorage.setItem("user", login.data.userName);
        sessionStorage.setItem("key", login.data.key);
        sessionStorage.setItem("roles", JSON.stringify(login.data.roles));
        navigate("/menu");
      });
    } catch (error) {
      swal(
        "Acceso No Autorizado",
        JSON.parse(error.request.response).message,
        "error"
      );
    }
  };

  return (
    <div className="login">
      <div className="form-container">
        <form onSubmit={ingresar} className="form">
          <label className="label">
            Usuario
          </label>
          <input
            value={user}
            onChange={(e) => setUser(e.target.value)}
            type="text"
            id="email"
            placeholder="Username"
            className="input input-email"
          />
          <label className="label">
            Contraseña
          </label>
          <input
            value={pwd}
            onChange={(e) => setPwd(e.target.value)}
            type="password"
            id="password"
            placeholder="**********"
            className="input input-password"
          />
          <button type="submit" className="btn btn-success">
            Iniciar Sesion
          </button>
        </form>
        <Link className="btn btn-secondary" to="/registro">
        Registrarse
        </Link>
      </div>
    </div>
  );
};

export default Login;
