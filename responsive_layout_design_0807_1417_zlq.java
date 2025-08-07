// 代码生成时间: 2025-08-07 14:17:43
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

// ResponsiveLayoutRepository 接口继承 JpaRepository，用于数据库操作
@Repository
public interface ResponsiveLayoutRepository extends JpaRepository<ResponsiveLayout, Long> {
    // 根据布局名称查找响应式布局
    @Query("SELECT r FROM ResponsiveLayout r WHERE r.name = :name")
    ResponsiveLayout findByName(@Param("name\) String name);
}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ResponsiveLayoutService 提供响应式布局相关业务逻辑
@Service
public class ResponsiveLayoutService {
    
    // 自动注入 ResponsiveLayoutRepository
    @Autowired
    private ResponsiveLayoutRepository responsiveLayoutRepository;

    // 获取所有响应式布局
    public List<ResponsiveLayout> getAllResponsiveLayouts() {
        return responsiveLayoutRepository.findAll();
    }

    // 根据名称查找响应式布局
    public ResponsiveLayout getResponsiveLayoutByName(String name) {
        return responsiveLayoutRepository.findByName(name);
    }

    // 添加响应式布局
    @Transactional
    public ResponsiveLayout addResponsiveLayout(ResponsiveLayout layout) {
        return responsiveLayoutRepository.save(layout);
    }

    // 更新响应式布局
    @Transactional
    public ResponsiveLayout updateResponsiveLayout(Long id, ResponsiveLayout layout) {
        // 错误处理
        if (layout == null || layout.getId() == null || !layout.getId().equals(id)) {
            throw new IllegalArgumentException("Invalid layout data");
        }
        return responsiveLayoutRepository.save(layout);
    }

    // 删除响应式布局
    @Transactional
    public void deleteResponsiveLayout(Long id) {
        // 错误处理
        if (id == null) {
            throw new IllegalArgumentException("Invalid layout ID");
        }
        ResponsiveLayout layout = responsiveLayoutRepository.findById(id).orElse(null);
        if (layout == null) {
            throw new IllegalArgumentException("Layout with ID not found");
        }
        responsiveLayoutRepository.delete(layout);
    }
}

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

// ResponsiveLayout 实体类，代表响应式布局实体
@Entity
public class ResponsiveLayout implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String cssClass;
    // 省略 getter 和 setter 方法
    // 省略构造函数
}
