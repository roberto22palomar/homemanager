// Conectarse a la base de datos best_travel
db = db.getSiblingDB('best_travel');

// Crear colección de Usuarios
db.createCollection('usuarios');
db.usuarios.insertMany([
  {
    nombre: 'Roberto',
    email: 'roberto@example.com',
    contraseña: 'hashed_password1',  // Recuerda usar hashes en producción
    casas: [],  // Se llenará después
    puntos_por_casa: {}
  },
  {
    nombre: 'Maria',
    email: 'maria@example.com',
    contraseña: 'hashed_password2',
    casas: [],
    puntos_por_casa: {}
  }
]);

// Crear colección de Casas
db.createCollection('casas');
db.casas.insertMany([
  {
    nombre: 'Casa Familiar',
    miembros: [],  // Se llenará después
    tareas: []
  },
  {
    nombre: 'Piso de Amigos',
    miembros: [],
    tareas: []
  }
]);

// Crear colección de Tareas
db.createCollection('tareas');
db.tareas.insertMany([
  {
    nombre: 'Lavar los platos',
    descripcion: 'Lavar todos los platos del día',
    fecha_asignada: new Date(),
    fecha_vencimiento: new Date(new Date().setDate(new Date().getDate() + 1)),  // Mañana
    periodicidad: 'diaria',
    estado: 'pendiente',
    usuario_asignado: null,  // Se asignará más tarde
    casa_id: null
  },
  {
    nombre: 'Sacar la basura',
    descripcion: 'Sacar la basura orgánica y reciclaje',
    fecha_asignada: new Date(),
    fecha_vencimiento: new Date(new Date().setDate(new Date().getDate() + 2)),  // En dos días
    periodicidad: 'semanal',
    estado: 'pendiente',
    usuario_asignado: null,
    casa_id: null
  }
]);

// Asignar usuarios a casas
let casaFamiliar = db.casas.findOne({ nombre: 'Casa Familiar' });
let pisoAmigos = db.casas.findOne({ nombre: 'Piso de Amigos' });
let roberto = db.usuarios.findOne({ nombre: 'Roberto' });
let maria = db.usuarios.findOne({ nombre: 'Maria' });

db.casas.updateOne({ _id: casaFamiliar._id }, { $set: { miembros: [roberto._id, maria._id] } });
db.casas.updateOne({ _id: pisoAmigos._id }, { $set: { miembros: [roberto._id] } });

db.usuarios.updateOne({ _id: roberto._id }, { $set: { casas: [casaFamiliar._id, pisoAmigos._id] } });
db.usuarios.updateOne({ _id: maria._id }, { $set: { casas: [casaFamiliar._id] } });

// Asignar tareas a casas y usuarios
let lavarPlatos = db.tareas.findOne({ nombre: 'Lavar los platos' });
let sacarBasura = db.tareas.findOne({ nombre: 'Sacar la basura' });

db.tareas.updateOne({ _id: lavarPlatos._id }, { $set: { usuario_asignado: roberto._id, casa_id: casaFamiliar._id } });
db.tareas.updateOne({ _id: sacarBasura._id }, { $set: { usuario_asignado: maria._id, casa_id: casaFamiliar._id } });

db.casas.updateOne({ _id: casaFamiliar._id }, { $set: { tareas: [lavarPlatos._id, sacarBasura._id] } });
