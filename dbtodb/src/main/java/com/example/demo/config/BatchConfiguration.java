package com.example.demo.config;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Employee;
import com.example.demo.model.User;

@Configuration
public class BatchConfiguration {
	@Autowired
	@Qualifier("userDs")
	DataSource userdataSource;
	
	@Bean
	Job job(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory
			,ItemReader<User> itemReader,ItemWriter<Employee> itemWriter
			,ItemProcessor<User, Employee> itemProcessor) {
		
		Step step =stepBuilderFactory.get("STEP")
					.<User,Employee>chunk(null)
					.reader(itemReader)
					.processor(itemProcessor)
					.writer(itemWriter)
					.build();
		return jobBuilderFactory.get("JOB Should Be Done")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}
	@Bean
	public ItemReader<User> itemReader( ) {
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<>();
       
        String FETCH_SQL_QUERY = "SELECT * FROM user";
         reader.setSql(FETCH_SQL_QUERY);
         reader.setDataSource(userdataSource);
         reader.setRowMapper(new UserDataRowmapper());
         return reader;
}
@Bean
public ItemWriter<Employee> itemWriter(@Qualifier("empDs") final DataSource dataSource){
	JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<Employee>();
	writer.setDataSource(dataSource);
	writer.setSql("INSERT INTO employee (ID, name, empDep) VALUES (:id, :name, :empDep)");
	writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
	return writer;
}


}
