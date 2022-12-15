import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import swal from "sweetalert";

const Menu = () => {
  let tipoUsuario = [];
  const navigate = useNavigate();
  const [salir, setSalir] = useState(0);
  try {
    JSON.parse(sessionStorage.getItem("roles")).forEach((element) => {
      if (element.nombreRol === "ADMIN") {
        tipoUsuario.push("admin");
      } 
      if (element.nombreRol === "PACIENTE") {
        tipoUsuario.push("paciente");
      } 
    });
  } catch (error) {}
  useEffect(() => {
    if (sessionStorage.getItem("key") === null) {
      swal("Saliendo de la aplicacion", "Debe digitar credenciales", "success");
      navigate("/");
    }
  }, [salir]);
  const cerrarSesion = () => {
    sessionStorage.clear();
    setSalir(1);
  };

  return (
    <div className="container-menu">
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          {tipoUsuario.includes("admin") ? (
            <Link className="navbar-brand" to="/pacientes">
              Pacientes
            </Link>
          ) : (
            ""
          )}
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
           {tipoUsuario.includes("admin") ? (
              <Link className="navbar-brand" to="/medicos">
                Medicos
              </Link>
            ) : (
              ""
            )}
            {tipoUsuario.includes("paciente") ? (
              <Link className="navbar-brand" to="/agendar">
                Agendar
              </Link>
            ) : (
              ""
            )}
            {tipoUsuario.includes("paciente") ? (
              <Link className="navbar-brand" to="/cancelar">
                Cancelar
              </Link>
            ) : (
              ""
            )}
            {tipoUsuario.includes("paciente") ? (
              <Link className="navbar-brand" to="/consultar">
                Consultar
              </Link>
            ) : (
              ""
            )}
            <Link className="navbar-brand" onClick={cerrarSesion}>
              Salir
            </Link>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default Menu;
