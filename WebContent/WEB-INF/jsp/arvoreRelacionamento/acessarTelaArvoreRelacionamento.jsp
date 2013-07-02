<%@ include file="/base.jsp" %> 

<link type="text/css" href="<c:url value="/css/jquery.treeview.css"/>" rel="stylesheet" />
<link type="text/css" href="<c:url value="/css/screen.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<c:url value="/js/jquery.cookie.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.treeview.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/arvore.js"/>"></script>

<h3 style="font-size: 20px;" > Árvore de relacionamentos </h3>

<ul id="gray" class="treeview-gray">
	<li class="closed">
	<li class="closed" >
		<span>Item 2</span>
		<ul>
			<li class="closed">
				<span>Item 2.1 (closed at start)</span>
				<ul>
					<li>Item 2.1.1</li>
					<li>Item 2.1.2</li>
				</ul>
			</li>
			<li>Item 2.2.1</li>
			<li>Item 2.2.2</li>
			<li>Item 2.2.3</li>
			<li>Item 2.2.4</li>
			<li>Item 2.2.5</li>
			<li>Item 2.2.6</li>
			<li>Item 2.2.7</li>
			<li>Item 2.2.8</li>
			<li>
				<span>Item 2.3</span>
				<ul>
					<li>Item 2.3.1</li>
					<li>Item 2.3.2</li>
					<li>Item 2.3.3</li>
					<li>Item 2.3.4</li>
					<li>Item 2.3.5</li>
					<li>Item 2.3.6</li>
					<li>Item 2.3.7</li>
					<li>Item 2.3.8</li>
					<li>Item 2.3.9</li>
				</ul>
			</li>
			<li>
				<span>Item 2.4</span>
				<ul>
					<li>Item 2.4.1</li>
					<li>Item 2.4.2</li>
					<li>Item 2.4.3</li>
					<li>Item 2.4.4</li>
					<li>Item 2.4.5</li>
					<li>Item 2.4.6</li>
					<li>Item 2.4.7</li>
					<li>Item 2.4.8</li>
					<li>Item 2.4.9</li>
				</ul>
			</li>
		</ul>
	</li>
	<li>Item 3</li>
</ul>	