import psycopg2

conn = psycopg2.connect(
    database='test_db',
    user='root',
    password='root',
    host='localhost'
)

cur = conn.cursor()

cur.execute("""
    CREATE TABLE IF NOT EXISTS users (
        id SERIAL PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        email VARCHAR(50) UNIQUE NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    """)

# Insert a single user
cur.execute("""
    INSERT INTO users (username, email)
    VALUES ('woho2', 'woho2@example.com');
    """)

# Commit the transaction
conn.commit()

# Fetch and print query result
cur.execute("SELECT * FROM users;")
users = cur.fetchall()
for user in users:
    print(user)

# Close the cursor and connection
cur.close()
conn.close()

