create table mate (
                      created_date datetime(6),
                      modified_date datetime(6),
                      id varbinary(16) not null,
                      mentee_id binary(16),
                      mentor_id binary(16),
                      status enum ('DEMANDED','ACCEPTED','REJECTED','CANCELLED'),
                      primary key (id)
);

create table mentoring (
                           mentoring_hour integer,
                           created_date datetime(6),
                           modified_date datetime(6),
                           start_date_time datetime(6),
                           evaluation_id varbinary(16),
                           id varbinary(16) not null,
                           mate_id varbinary(16) not null,
                           mentoring_status enum ('DEMANDED','ACCEPTED','CONFIRMED','COMPLETED','CANCELLED'),
                           primary key (id)
);

create table mentoring_evaluation (
                                      score integer,
                                      created_date datetime(6),
                                      modified_date datetime(6),
                                      id varbinary(16) not null,
                                      review varchar(255),
                                      primary key (id)
);

alter table mentoring
    add constraint uk_mentoring_evaluation_id unique (evaluation_id);

alter table mentoring
    add constraint fk_mentoring_to_evaluation
        foreign key (evaluation_id)
            references mentoring_evaluation (id);

alter table mentoring
    add constraint fk_mentoring_to_mate
        foreign key (mate_id)
            references mate (id);
