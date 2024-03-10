CREATE FUNCTION update_at_column() RETURNS trigger AS $$
BEGIN
    NEW.updated_at := NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
