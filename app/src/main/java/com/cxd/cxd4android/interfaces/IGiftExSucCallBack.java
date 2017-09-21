package com.cxd.cxd4android.interfaces;
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



/**
 * @ClassName: IGiftExSucCallBack
 * @Description: 商品兑换成功回调
 * @Author：GeLe
 * @Date：2016-4-6 上午10:40:37
 * @version V1.3.5
 *
 */
public class IGiftExSucCallBack {

	private String type;

	public IGiftExSucCallBack(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
