CREATE TABLE Categoria (
    id_categoria INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE Atividade (
    id_atividade INT PRIMARY KEY,
    conteudo TEXT NOT NULL
);

CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    sexo CHAR(1) NOT NULL,
    data_nascimento DATE NOT NULL
);

CREATE TABLE Video (
    id_video INT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    url VARCHAR(255) NOT NULL,
    id_categoria INT,
    id_atividade INT,
    id_usuario INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria),
    FOREIGN KEY (id_atividade) REFERENCES Atividade(id_atividade),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Favorito (
    id_favorito INT PRIMARY KEY,
    data_favoritado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT,
    id_video INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_video) REFERENCES Video(id_video)
);

CREATE TABLE Feedback (
    id_feedback INT PRIMARY KEY,
    id_video INT NOT NULL,
    conteudo TEXT NOT NULL,
    data_feedback TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_video) REFERENCES Video(id_video)
);

