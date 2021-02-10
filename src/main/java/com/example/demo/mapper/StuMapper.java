package com.example.demo.mapper;

import java.util.List;

import com.example.demo.bean.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StuMapper {
	@Insert("insert into t_student values(#{stuno},#{stuname},#{gradeid})")
	public boolean addStu(Student student);
	@Delete("delete from t_student where stuno = #{stuno}")
	public boolean deleteStuByStuno(int stuno);
	@Update("update t_student set stuname=#{stuname},gradeid=#{gradeid} where stuno = #{stuno}")
	public boolean updateStuByStuno(Student student);
	@Select("select * from t_student")
	public List<Student> queryStus() ;
	@Select("select * from t_student where stuno = #{stuno}")
	public Student queryStuByStuno(Integer stuno) ;
}
