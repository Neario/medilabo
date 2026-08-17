CREATE TABLE IF NOT EXISTS app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identifier VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ORGANIZER','PRACTITIONER') NOT NULL
);

INSERT INTO app_user (identifier, password, role) VALUES
('Organizer', '$2a$10$AyoVTrD.JeKpPJBmPJLMhuCqtXQXircLEpUcPHPIrN6uS.3Nv6tL.', 'ORGANIZER'),
('Practitioner', '$2a$10$cWDbznitwY899Gs/xjURXOudIo9WlYfNB3cS7DwqSf87tEPNQL/6e', 'PRACTITIONER');
