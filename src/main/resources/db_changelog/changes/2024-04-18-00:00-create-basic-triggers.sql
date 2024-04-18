--liquibase formatted sql
--changeset samuelcluna:create-basic-triggers context:new splitStatements:true endDelimiter:;

-- UpdateColumnTrigger for SaceUser

CREATE TRIGGER sace_user_updated_at_column
    BEFORE UPDATE
    ON sace_user
    FOR EACH ROW
    EXECUTE PROCEDURE update_at_column();

-- UpdateColumnTrigger for SignUpConfirmationCode

CREATE TRIGGER sign_up_confirmation_code_updated_at_column
    BEFORE UPDATE
    ON sign_up_confirmation_code
    FOR EACH ROW
    EXECUTE PROCEDURE update_at_column();

-- UpdateColumnTrigger for Court

CREATE TRIGGER court_updated_at_column
    BEFORE UPDATE
    ON court
    FOR EACH ROW
    EXECUTE PROCEDURE update_at_column();

-- UpdateColumnTrigger for Reservation


CREATE TRIGGER reservation_updated_at_column
    BEFORE UPDATE
    ON reservation
    FOR EACH ROW
    EXECUTE PROCEDURE update_at_column();

