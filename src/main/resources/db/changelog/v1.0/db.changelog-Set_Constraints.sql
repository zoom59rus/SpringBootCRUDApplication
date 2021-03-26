ALTER TABLE users
    ADD CONSTRAINT fk_users_accounts
        FOREIGN KEY (account_id) REFERENCES accounts (id)
            ON DELETE SET NULL;

ALTER TABLE files
    ADD CONSTRAINT fk_files_users
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE SET NULL;

ALTER TABLE events
    ADD CONSTRAINT fk_events_users
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE SET NULL;

ALTER TABLE events
    ADD CONSTRAINT fk_events_files
        FOREIGN KEY (file_id) REFERENCES files (id)
            ON DELETE SET NULL;

ALTER TABLE accounts_roles
    ADD CONSTRAINT fk_accounts_roles_users
        FOREIGN KEY (account_id) REFERENCES accounts (id)
            ON DELETE CASCADE;

ALTER TABLE accounts_roles
    ADD CONSTRAINT fk_accounts_roles_roles
        FOREIGN KEY (role_id) REFERENCES roles (id)
            ON DELETE CASCADE;