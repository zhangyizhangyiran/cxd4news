/*
 * Copyright (C) 2013 www.jryghq.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cxd.cxd4android.model;
/**
 * @ClassName: BaseModel
 * @Description: 数据格式
 * @Author：GeLe
 * @Date：2015-7-1 下午1:36:15
 * @version V1.0
 *
 */
public class BaseModel<T> {
	/**
	 * status		分为以下状态:
	 * 				200: 调用成功
	 * 				500: 预期失败
	 * 				404: 系统错误
	 *
	 * msg			结果信息/错误信息
	 * result		数据
	 */
	public String msg = "";

	public String status = "";

	public T result ;
}
