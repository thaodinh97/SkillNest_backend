CREATE TABLE users
(
    id            UUID NOT NULL,
    full_name     VARCHAR(255),
    avatar_url    VARCHAR(255),
    email         VARCHAR(255),
    password      VARCHAR(255),
    date_of_birth date,
    phone_number  VARCHAR(255),
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE invalidated_token
(
    id         VARCHAR(255) NOT NULL,
    expiration TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_invalidatedtoken PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    roles_name VARCHAR(255) NOT NULL,
    user_id    UUID         NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (roles_name, user_id)
);

CREATE TABLE role
(
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_role PRIMARY KEY (name)
);

CREATE TABLE permission
(
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_permission PRIMARY KEY (name)
);

CREATE TABLE role_permissions
(
    permission_name VARCHAR(255) NOT NULL,
    role_name       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role_permissions PRIMARY KEY (permission_name, role_name)
);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_rolper_on_permission FOREIGN KEY (permission_name) REFERENCES permission (name);

ALTER TABLE role_permissions
    ADD CONSTRAINT fk_rolper_on_role FOREIGN KEY (role_name) REFERENCES role (name);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_name) REFERENCES role (name);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);


CREATE TABLE courses
(
    id            UUID             NOT NULL,
    title         VARCHAR(250)     NOT NULL,
    thumbnail_url VARCHAR(255),
    description   TEXT,
    price         DOUBLE PRECISION NOT NULL,
    is_published  BOOLEAN,
    instructor_id UUID             NOT NULL,
    student_count BIGINT,
    CONSTRAINT pk_courses PRIMARY KEY (id)
);

ALTER TABLE courses
    ADD CONSTRAINT FK_COURSES_ON_INSTRUCTOR FOREIGN KEY (instructor_id) REFERENCES users (id);

CREATE TABLE course_sections
(
    id            UUID         NOT NULL,
    title         VARCHAR(255) NOT NULL,
    section_order INTEGER,
    course_id     UUID,
    CONSTRAINT pk_course_sections PRIMARY KEY (id)
);

ALTER TABLE course_sections
    ADD CONSTRAINT FK_COURSE_SECTIONS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

CREATE TABLE reviews
(
    id         UUID                        NOT NULL,
    rating     INTEGER                     NOT NULL,
    comment    VARCHAR(2000),
    user_id    UUID,
    course_id  UUID,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (id)
);

ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE orders
(
    id             UUID                        NOT NULL,
    user_id        UUID,
    total_amount   DOUBLE PRECISION            NOT NULL,
    payment_status VARCHAR(255)                NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE order_items
(
    id        UUID             NOT NULL,
    order_id  UUID,
    course_id UUID,
    price     DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (id)
);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

CREATE TABLE lessons
(
    id              UUID         NOT NULL,
    title           VARCHAR(200) NOT NULL,
    lesson_order    INTEGER      NOT NULL,
    video_public_id VARCHAR(255),
    video_url       VARCHAR(255),
    content         TEXT,
    section_id      UUID,
    CONSTRAINT pk_lessons PRIMARY KEY (id)
);

ALTER TABLE lessons
    ADD CONSTRAINT FK_LESSONS_ON_SECTION FOREIGN KEY (section_id) REFERENCES course_sections (id);

CREATE TABLE lesson_progress
(
    id           UUID    NOT NULL,
    lesson_id    UUID,
    user_id      UUID,
    completed    BOOLEAN NOT NULL,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_lesson_progress PRIMARY KEY (id)
);

ALTER TABLE lesson_progress
    ADD CONSTRAINT FK_LESSON_PROGRESS_ON_LESSON FOREIGN KEY (lesson_id) REFERENCES lessons (id);

ALTER TABLE lesson_progress
    ADD CONSTRAINT FK_LESSON_PROGRESS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE enrollments
(
    id                  UUID                        NOT NULL,
    student_id          UUID,
    course_id           UUID,
    enrolled_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    progress_percentage INTEGER,
    CONSTRAINT pk_enrollments PRIMARY KEY (id)
);

ALTER TABLE enrollments
    ADD CONSTRAINT FK_ENROLLMENTS_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE enrollments
    ADD CONSTRAINT FK_ENROLLMENTS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES users (id);

CREATE TABLE cart
(
    id      UUID NOT NULL,
    user_id UUID,
    CONSTRAINT pk_cart PRIMARY KEY (id)
);

ALTER TABLE cart
    ADD CONSTRAINT uc_cart_user UNIQUE (user_id);

ALTER TABLE cart
    ADD CONSTRAINT FK_CART_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE cart_item
(
    id        UUID NOT NULL,
    cart_id   UUID,
    course_id UUID,
    added_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_cart_item PRIMARY KEY (id)
);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CART_ITEM_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CART_ITEM_ON_COURSE FOREIGN KEY (course_id) REFERENCES courses (id);