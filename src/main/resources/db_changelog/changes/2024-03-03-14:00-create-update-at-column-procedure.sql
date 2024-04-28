--liquibase formatted sql
--changeset samuelcluna:create-update-at-column-procedure context:new splitStatements:true endDelimiter:;


-- UpdateAtColumnProcedure

CREATE FUNCTION update_at_column() RETURNS trigger AS $$
BEGIN
    NEW.updated_at := NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
