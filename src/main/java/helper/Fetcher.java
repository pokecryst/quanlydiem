package helper;

import dao.ClassesDao;
import dao.EnrollStuDao;

public class Fetcher {
	
	public <T> T fetchDao(Class<T> daoType) {
        if (daoType.equals(EnrollStuDao.class)) {
            return daoType.cast(new EnrollStuDao());
        } else if (daoType.equals(ClassesDao.class)) {
            return daoType.cast(new ClassesDao());
        } else {
            throw new IllegalArgumentException("Unsupported DAO type: " + daoType.getName());
        }
    }
}
