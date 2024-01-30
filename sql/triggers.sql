CREATE OR REPLACE FUNCTION get_role_id_by_name(varchar(100))
    RETURNS TABLE(role_id integer) AS $$
    SELECT role_id::integer FROM roles WHERE role_name = $1;
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION create_new_user()
    RETURNS trigger AS $$
DECLARE
    user_id_var integer;
    role_id_var integer;
BEGIN
    user_id_var = NEW.user_id;
    role_id_var = get_role_id_by_name('user');
    INSERT INTO user_roles(user_role_id, user_id, role_id) VALUES
        (default, user_id_var, role_id_var);
    INSERT INTO accounts(account_id, user_id, account_balance) VALUES
        (default, user_id_var, 0);
    RETURN NEW;
END
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER create_new_user_trigger
    AFTER INSERT ON users
    FOR EACH ROW
    EXECUTE FUNCTION create_new_user();
